import { User } from "@/models/user";
import { url } from "@/url";
import { reactive } from "vue";

export interface UserForm {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
}

export interface LoginForm {
  username: string;
  password: string;
}

export class Session {
  public user: User | null = null;
  public token: string | null = null;

  public register(form: UserForm): Promise<User> {
    return fetch(url("/users/"), {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    })
      .then((response: Response) => response.json())
      .then((data: Record<string, never>) => User.wrap(data));
  }

  public login(form: LoginForm): Promise<User> {
    return fetch(url("/session/"), {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    })
      .then((response: Response) => response.json())
      .then((data: Record<string, never>) => {
        this.token = data["token"];
        this.user = User.wrap(data["user"]);
        return this.user;
      });
  }

  public logout(): void {
    this.user = null;
    this.token = null;
  }
}

export const session = reactive(new Session());
