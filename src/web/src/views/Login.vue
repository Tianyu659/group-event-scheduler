<template>
  <div class="small">
    <h1>Login</h1>
    <div class="form">
      <div class="group">
        <input
          id="username"
          type="text"
          placeholder="username"
          v-model="credentials.username"
        />
        <label>Username</label>
      </div>
      <div class="group">
        <input
          id="password"
          type="password"
          placeholder="password"
          v-model="credentials.password"
        />
        <label>Password</label>
      </div>
      <div class="buttons">
        <button @click="onClickSubmit">Submit</button>
        <router-link to="/register" class="button">Register</router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";

@Options({})
export default class Login extends Vue {
  public credentials = { username: "", password: "" };

  public onClickSubmit(): void {
    session.login(this.credentials).then(() => {
      this.$router.push({ name: "home" });
    });
  }
}
</script>

<style scoped lang="scss"></style>
