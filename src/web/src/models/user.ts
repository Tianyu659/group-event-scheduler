export class Blackout {
  public constructor(public start: Date, public end: Date) {}

  public static wrap(data: Record<string, any>): Blackout {
    return new Blackout(
      new Date(data["start"] * 1000),
      new Date(data["end"] * 1000)
    );
  }
}

export class User {
  public id: number;
  public username: string;
  public firstName: string;
  public lastName: string;
  public blocked: Array<string>;
  public blackouts: Array<Blackout>;

  public constructor(
    id: number,
    username: string,
    firstName: string,
    lastName: string,
    blocked: Array<string> = [],
    blackouts: Array<Blackout> = []
  ) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.blocked = blocked;
    this.blackouts = blackouts;
  }

  public static wrap(data: Record<string, any>): User {
    return new User(
      data["id"],
      data["username"],
      data["firstName"],
      data["lastName"],
      data["blocked"],
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
}
