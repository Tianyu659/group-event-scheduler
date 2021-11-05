<template>
  <div>
    <h1>Hi, {{ session.user.firstName }}</h1>
    <p>
      Welcome back to Groupie! To get started, use the below menus to create or
      manage your events. Don't forget you can have multiple events open at
      once.
    </p>
    <div>
      <h2>My Events</h2>
      <p>You don't have any events yet. Create one below!</p>
    </div>
    <div>
      <h2>New Event</h2>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";

@Options({})
export default class Home extends Vue {
  public readonly session = session;
}
</script>

<style scoped></style>
