import { User } from "@/models/user";

export class Invitation {
  public constructor(
    public id: number,
    public groupDate: GroupDate,
    public user: User
  ) {}

  public dump(): Record<string, any> {
    return { username: this.user.username };
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

export class GroupDateEvent {
  public constructor(
    public id: number,
    public groupDate: GroupDate,
    public eid: string,
    public url: string,
    public name: string,
    public description: string,
    public location: string,
    public time: Date,
    public duration: number
  ) {}

  public static empty(groupDate: GroupDate): GroupDateEvent {
    return new GroupDateEvent(0, groupDate, "", "", "", "", "", new Date(), 60);
  }

  public static ticketmaster(
    groupDate: GroupDate,
    data: Record<string, any>
  ): GroupDateEvent {
    const venue = getVenue(data);
    const location = formatLocation(venue);

    return new GroupDateEvent(
      0,
      groupDate,
      data["id"],
      data["url"],
      data["name"],
      data["info"],
      location,
      new Date(data["dates"]["start"]["dateTime"]),
      60
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
}

export class GroupDate {
  public constructor(
    public id: number,
    public name: string,
    public description: string,
    public live: boolean,
    public creator: User,
    public events: Array<GroupDateEvent>,
    public invitations: Array<Invitation>
  ) {}

  public static empty(creator: User): GroupDate {
    return new GroupDate(0, "", "", false, creator, [], []);
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
