<template>
  <div class="form">
    <h4>Invite a User</h4>
    <div class="form-group">
      <input
        id="search-users-invite"
        type="text"
        placeholder="name"
        v-model="search"
        @input="filter"
      />
      <label>Search for a user</label>
    </div>
    <ul
      id="invited-users"
      class="fancy click"
      v-show="filteredUsers.length > 0"
    >
      <li
        v-for="user of filteredUsers"
        :key="user.id"
        :class="{ disabled: user.blocks(session.user.username) }"
        @click="onClickUser(user)"
      >
        <span>
          {{ user.firstName }} {{ user.lastName }} ({{ user.username }})
        </span>
        <span v-show="user.blocks(session.user.username)" class="float-right">
          unavailable
        </span>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { GroupDate, Invitation } from "@/models/groupDate";
import { Prop } from "vue-property-decorator";
import { url } from "@/url";
import { session } from "@/session";
import { User } from "@/models/user";

@Options({})
export default class GroupDateFormInvitationForm extends Vue {
  @Prop() public groupDate!: GroupDate;
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
    if (!user.blocks(session.user!.username)) {
      this.$emit("submit", new Invitation(0, this.groupDate, user));
    }
  }
}
</script>

<style scoped></style>
