export class User {
  public id: number;
  public username: string;
  public firstName: string;
  public lastName: string;

  public constructor(
    id: number,
    username: string,
    firstName: string,
    lastName: string
  ) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public static wrap(data: Record<string, never>): User {
    return new User(
      data["id"],
      data["username"],
      data["firstName"],
      data["lastName"]
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
