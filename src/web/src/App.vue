<template>
  <div id="navigation">
    <router-link v-if="session.user === null" to="/login">Groupie</router-link>
    <router-link v-else :to="{ name: 'dashboard' }">Groupie</router-link>
    <div class="spacer" />
    <router-link to="/profile" v-if="session.user !== null">
      {{ session.user.firstName }}
    </router-link>
    <a
      id="logout"
      v-if="session.user !== null"
      class="cursor-pointer"
      @click="logout()"
    >
      Logout
    </a>
    <router-link to="/login" v-if="session.user === null">Login</router-link>
  </div>
  <router-view id="content" />
  <div id="footer">Created by Team 54</div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";

@Options({})
export default class Home extends Vue {
  public readonly session = session;

  public logout(): void {
    this.$router.push({ name: "login" }).then(() => session.logout());
  }
}
</script>

<style lang="scss" src="./styles/index.scss"></style>
