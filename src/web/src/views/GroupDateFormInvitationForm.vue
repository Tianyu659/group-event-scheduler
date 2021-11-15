<template>
  <div class="form">
    <h4>Invite a User</h4>
    <div class="form-group">
      <input type="text" placeholder="name" v-model="search" @input="filter" />
      <label>Search for a user</label>
    </div>
    <ul class="users">
      <li v-for="user of filteredUsers" :key="user.id">
        {{ user.firstName }} {{ user.lastName }}
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { GroupDate } from "@/models/groupDate";
import { Prop } from "vue-property-decorator";
import { url } from "@/url";
import { session } from "@/session";
import { User } from "@/models/user";

@Options({})
export default class GroupDateFormInvitationForm extends Vue {
  @Prop() public groupDate!: GroupDate;
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
}
</script>

<style scoped></style>
