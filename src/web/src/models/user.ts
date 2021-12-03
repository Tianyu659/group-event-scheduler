export class Blackout {
  public constructor(public id: number, public start: Date, public end: Date) {
    console.log(start, end);
  }

  public static wrap(data: Record<string, any>): Blackout {
    const offset = new Date().getTimezoneOffset() * 60 * 1000;
    return new Blackout(
      data["id"],
      new Date(data["start"] * 1000 + offset),
      new Date(data["end"] * 1000 + offset)
    );
  }
}

export class BlockedUser {
  public constructor(
    public id: number, // Not user ID
    public username: string,
    public firstName: string,
    public lastName: string
  ) {}

  public static wrap(data: Record<string, any>): BlockedUser {
    return new BlockedUser(
      data["id"],
      data["userUsername"],
      data["userFirstName"],
      data["userLastName"]
    );
  }
}

export class User {
  public id: number;
  public username: string;
  public firstName: string;
  public lastName: string;
  public blocked: Array<BlockedUser>;
  public blackouts: Array<Blackout>;
  private blockedUsernames: Set<string>;

  public constructor(
    id: number,
    username: string,
    firstName: string,
    lastName: string,
    blocked: Array<BlockedUser> = [],
    blackouts: Array<Blackout> = []
  ) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.blocked = blocked;
    this.blackouts = blackouts;
    this.blockedUsernames = new Set();
    this.updateBlockedUsernames();
  }

  public overwrite(data: Record<string, any>): void {
    this.username = data["username"];
    this.firstName = data["firstName"];
    this.lastName = data["lastName"];
    this.blocked = data["blocked"].map(BlockedUser.wrap);
    this.blackouts = data["blackouts"].map(Blackout.wrap);
    this.updateBlockedUsernames();
  }

  public static wrap(data: Record<string, any>): User {
    return new User(
      data["id"],
      data["username"],
      data["firstName"],
      data["lastName"],
      data["blocked"].map(BlockedUser.wrap),
      data["blackouts"].map(Blackout.wrap)
    );
  }

  public matches(search: string): boolean {
    search = search.toLowerCase();
    return (
      this.username.toLowerCase().indexOf(search) > -1 ||
      this.firstName.toLowerCase().indexOf(search) > -1 ||
      this.lastName.toLowerCase().indexOf(search) > -1
    );
  }

  public blocks(username: string): boolean {
    return this.blockedUsernames.has(username) || this.blackout();
  }

  public blackout(): boolean {
    for (const blackout of this.blackouts) {
      const actualEnd = new Date(blackout.end);
      actualEnd.setDate(blackout.end.getDate() + 1);
      if (blackout.start <= new Date() && new Date() <= actualEnd) {
        return true;
      }
    }
    return false;
  }

  private updateBlockedUsernames(): void {
    this.blockedUsernames.clear();
    for (const blockedUser of this.blocked) {
      this.blockedUsernames.add(blockedUser.username);
    }
  }
}
