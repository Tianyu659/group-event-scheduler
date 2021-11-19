<template>
  <div class="small">
    <div class="logo spaced">
      <span>Groupie</span>
    </div>
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
      <p class="error" v-show="error">{{ error }}</p>
      <div class="group buttons">
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
  public error = "";

  public onClickSubmit(): void {
    session
      .login(this.credentials)
      .then(() => {
        this.$router.push({ name: "dashboard" });
      })
      .catch((data: Record<string, never>) => {
        console.log("error", data);
        this.error = data["error"];
      });
  }
}
</script>
