import { User } from "@/models/user";

export class Invitation {
  public constructor(public groupDate: GroupDate, public user: User) {}
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
}
