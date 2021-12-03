<template>
  <div class="form">
    <h4>Block a User</h4>
    <div class="form-group">
      <input
        id="search-users-block"
        type="text"
        placeholder="name"
        v-model="search"
        @input="filter"
      />
      <label>Search for a user</label>
    </div>
    <ul
      id="blocked-users"
      class="fancy click"
      v-show="filteredUsers.length > 0"
    >
      <li
        v-for="user of filteredUsers"
        :key="user.id"
        @click="onClickUser(user)"
      >
        {{ user.firstName }} {{ user.lastName }} ({{ user.username }})
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { url } from "@/url";
import { User } from "@/models/user";

@Options({})
export default class Home extends Vue {
  public readonly session = session;
  public search = "";
  public users: Array<User> = [];
  public filteredUsers: Array<User> = [];

  public created(): void {
    fetch(url("/users/"), {
      method: "GET",
      headers: { Authorization: session.token! },
    }).then((response: Response) => {
      response.json().then((data: Array<Record<string, never>>) => {
        this.users = data.map(User.wrap);
        this.filter();
      });
    });
  }

  public filter(): void {
    this.filteredUsers.length = 0;
    if (this.search.trim().length > 0) {
      for (const user of this.users) {
        if (
          user.id === session.user!.id ||
          session.user!.blocks(user.username)
        ) {
          continue;
        }
        if (user.matches(this.search.trim())) {
          this.filteredUsers.push(user);
        }
        if (this.filteredUsers.length >= 5) {
          break;
        }
      }
    }
  }

  public onClickUser(user: User): void {
    fetch(url(`/users/${this.session.user!.id}/blocks/`), {
      method: "POST",
      headers: { Authorization: session.token! },
      body: JSON.stringify({ username: user.username }),
    }).then(() => {
      this.search = "";
      this.users = [];
      this.filter();
      session.refresh();
    });
  }
}
</script>
