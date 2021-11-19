<template>
  <div>
    <h1>Hi, {{ session.user.firstName }}</h1>
    <p>
      Welcome back to Groupie! To get started, use the below menus to create or
      manage your events. Don't forget you can have multiple events open at
      once.
    </p>
    <div>
      <h2>
        My Group Dates
        <router-link :to="{ name: 'create' }">+</router-link>
      </h2>
      <p v-show="groupDates.length === 0">
        You don't have any group dates yet.
      </p>
      <ul class="fancy click">
        <li
          v-for="groupDate of groupDates"
          :key="groupDate.id"
          @click="onClickGroupDate(groupDate)"
        >
          {{ groupDate.name }}
        </li>
      </ul>
    </div>
    <div>
      <h2>My Invitations</h2>
      <p v-show="invitations.length === 0">
        You don't have any group date invitations yet.
      </p>
      <ul class="fancy click">
        <li
          v-for="invitation of invitations"
          :key="invitation.id"
          @click="onClickInvitation(invitation)"
        >
          {{ invitation.groupDate.name }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { url } from "@/url";
import { GroupDate, Invitation } from "@/models/groupDate";
import router from "@/router";

@Options({})
export default class Home extends Vue {
  public readonly session = session;
  public readonly groupDates: Array<GroupDate> = [];
  public readonly invitations: Array<Invitation> = [];

  public created(): void {
    fetch(url("/groupDates/"), {
      method: "GET",
      headers: { Authorization: session.token! },
    }).then((response: Response) => {
      response.json().then((data: Array<Record<string, never>>) => {
        this.groupDates.length = 0;
        this.groupDates.push(...data.map(GroupDate.wrap));
      });
    });
    fetch(url("/invitations/"), {
      method: "GET",
      headers: { Authorization: session.token! },
    }).then((response: Response) => {
      response.json().then((data: Array<Record<string, never>>) => {
        this.invitations.length = 0;
        this.invitations.push(
          ...data
            .map(Invitation.wrap)
            .filter((invitation: Invitation) => invitation.response === null)
        );
      });
    });
  }

  public onClickGroupDate(groupDate: GroupDate): void {
    router.push({ name: "date", params: { id: groupDate.id } });
  }

  public onClickInvitation(invitation: Invitation): void {
    router.push({ name: "invitation", params: { id: invitation.id } });
  }
}
</script>

<style scoped></style>
