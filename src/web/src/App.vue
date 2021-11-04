<template>
  <div id="navigation">
    <router-link v-if="session.user === null" to="/">Home</router-link>
    <router-link v-else to="/dashboard">Home</router-link>
    <div class="spacer" />
    <router-link to="/profile" v-if="session.user !== null">
      {{ session.user.firstName }}
    </router-link>
    <a
      v-if="session.user !== null"
      class="cursor-pointer"
      @click="session.logout()"
    >
      Logout
    </a>
    <router-link to="/login" v-if="session.user === null">Login</router-link>
  </div>
  <router-view id="content" />
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";

@Options({})
export default class Home extends Vue {
  public readonly session = session;

  public test(): void {
    console.log(session);
  }
}
</script>

<style lang="scss" src="./styles/index.scss"></style>
