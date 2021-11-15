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
