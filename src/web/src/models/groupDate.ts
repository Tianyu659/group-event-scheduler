import { User } from "@/models/user";

export class Invitation {
  public constructor(
    public id: number,
    public groupDate: GroupDate,
    public user: User,
    public response: InvitationResponse | null = null
  ) {}

  public static wrap(data: Record<string, any>): Invitation {
    return new Invitation(
      data["id"],
      GroupDate.wrap(data["groupDate"]),
      User.wrap(data["user"]),
      InvitationResponse.wrap(data["response"])
    );
  }

  public dump(): Record<string, any> {
    return { username: this.user.username };
  }
}

export class InvitationEventResponse {
  public constructor(
    public event: GroupDateEvent,
    public available: boolean,
    public interest: number
  ) {}

  public dump(): Record<string, any> {
    return {
      eventId: this.event.id,
      available: this.available,
      interest: this.interest,
    };
  }

  public static wrap(data: Record<string, any>): InvitationEventResponse {
    return new InvitationEventResponse(
      GroupDateEvent.wrap(data["event"]),
      data["available"],
      data["interest"]
    );
  }
}

export class InvitationResponse {
  public constructor(
    public accepted: boolean,
    public events: Array<InvitationEventResponse>
  ) {}

  public static create(invitation: Invitation): InvitationResponse {
    return new InvitationResponse(
      true,
      invitation.groupDate.events.map(
        (groupDateEvent: GroupDateEvent) =>
          new InvitationEventResponse(groupDateEvent, false, 1)
      )
    );
  }

  public static wrap(
    data: Record<string, any> | null
  ): InvitationResponse | null {
    if (data === null) {
      return null;
    } else {
      return new InvitationResponse(
        data["accepted"],
        data["events"].map(InvitationEventResponse.wrap)
      );
    }
  }

  public dump(): Record<string, any> {
    return {
      accepted: this.accepted,
      events: this.events.map((event: InvitationEventResponse) => event.dump()),
    };
  }
}

function getVenue(data: Record<string, any>): Record<string, any> | null {
  return data["_embedded"]["venues"][0] ?? null;
}

function formatLocation(venue: Record<string, any> | null): string {
  if (venue === null) {
    return "No location provided";
  }

  let result = venue["name"].trim();
  if (venue["city"]) {
    result += `, ${venue["city"]["name"].trim()}`;
  }

  if (venue["state"]) {
    result += `, ${venue["state"]["stateCode"].trim()}`;
  }

  return result;
}

function sum(values: Array<number>): number {
  let total = 0;
  for (const value of values) {
    total += value;
  }
  return total;
}

export class GroupDateEvent {
  public constructor(
    public id: number,
    public eid: string,
    public url: string,
    public name: string,
    public description: string,
    public location: string,
    public time: Date,
    public duration: number,
    public interest: Array<number> = []
  ) {}

  public static empty(): GroupDateEvent {
    return new GroupDateEvent(0, "", "", "", "", "", new Date(), 60);
  }

  public static ticketmaster(data: Record<string, any>): GroupDateEvent {
    const venue = getVenue(data);
    const location = formatLocation(venue);

    return new GroupDateEvent(
      0,
      data["id"],
      data["url"],
      data["name"],
      data["info"] || "",
      location,
      new Date(data["dates"]["start"]["dateTime"]),
      60
    );
  }

  public static wrap(data: Record<string, any>): GroupDateEvent {
    return new GroupDateEvent(
      data["id"],
      data["eid"],
      data["url"],
      data["name"],
      data["description"],
      data["location"],
      new Date(data["time"] * 1000),
      data["duration"],
      data["interest"]
    );
  }

  public dump(): Record<string, any> {
    return {
      eid: this.eid,
      url: this.url,
      name: this.name,
      description: this.description,
      location: this.location,
      time: this.time.getTime() / 1000,
      duration: this.duration,
    };
  }

  public get availableCount(): number {
    return this.interest.filter((n: number) => n > -1).length;
  }

  public get averageInterest(): number {
    return (
      Math.round(
        (sum(this.interest.filter((n: number) => n > -1)) /
          this.interest.length) *
          10
      ) / 10
    );
  }
}

export class GroupDate {
  public constructor(
    public id: number,
    public name: string,
    public description: string,
    public live: boolean,
    public creator: User,
    public events: Array<GroupDateEvent>,
    public invitations: Array<Invitation>,
    public invitationCount: [number, number] = [0, 0]
  ) {}

  public static empty(creator: User): GroupDate {
    return new GroupDate(0, "", "", true, creator, [], []);
  }

  public getBestEvent(): GroupDateEvent {
    const highestAttendanceValue = this.events
      .map((event: GroupDateEvent) => event.availableCount)
      .reduce(
        (a: number, b: number) => Math.max(a, b),
        this.events[0].availableCount
      );
    const highestAttendance = this.events.filter(
      (event: GroupDateEvent) => event.availableCount === highestAttendanceValue
    );
    if (highestAttendance.length === 1) {
      return highestAttendance[0];
    } else {
      highestAttendance.sort(
        (a: GroupDateEvent, b: GroupDateEvent) =>
          b.averageInterest - a.averageInterest
      );
      return highestAttendance[0];
    }
  }

  public static wrap(data: Record<string, any>): GroupDate {
    return new GroupDate(
      data["id"],
      data["name"],
      data["description"],
      data["live"],
      User.wrap(data["creator"]),
      data["events"].map(GroupDateEvent.wrap),
      [],
      data["invitationCount"]
    );
  }

  public dump(): Record<string, any> {
    return {
      name: this.name,
      description: this.description,
      live: this.live,
      events: this.events.map((event: GroupDateEvent) => event.dump()),
      invitations: this.invitations.map((invitation: Invitation) =>
        invitation.dump()
      ),
    };
  }
}
