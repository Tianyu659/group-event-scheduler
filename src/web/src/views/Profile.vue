<template>
  <div class="about">
    <h1>Settings</h1>
    <p>{{ session.user.firstName }}</p>
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
