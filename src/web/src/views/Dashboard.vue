<template>
  <div>
    <h1>Hi, {{ session.user.firstName }}</h1>
    <p>
      Welcome back to Groupie! To get started, use the below menus to create or
      manage your events. Don't forget you can have multiple events open at
      once.
    </p>
    <div>
      <h2>My Group Dates</h2>
      <p>You don't have any group dates yet.</p>
    </div>
    <div>
      <h2>My Invitations</h2>
      <p>You don't have any invitations yet.</p>
    </div>
    <div>
      <h2>Create an Event</h2>
      <group-date-form @refresh="refresh()" />
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import GroupDateForm from "@/views/GroupDateForm.vue";

@Options({
  components: { GroupDateForm },
})
export default class Home extends Vue {
  public readonly session = session;

  public refresh(): void {
    console.log("refreshing!");
  }
}
</script>

<style scoped></style>
