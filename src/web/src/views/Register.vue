<template>
  <div class="small">
    <h1>Register</h1>
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
      <div class="group">
        <input
          id="password2"
          type="password"
          placeholder="password"
          v-model="passwordConfirm"
        />
        <label>Confirm password</label>
      </div>
      <div class="group">
        <input
          id="first-name"
          type="text"
          placeholder="First name"
          v-model="credentials.firstName"
        />
        <label>First name</label>
      </div>
      <div class="group">
        <input
          id="last-name"
          type="text"
          placeholder="username"
          v-model="credentials.lastName"
        />
        <label>Last name</label>
      </div>
      <p class="error" v-show="error">{{ error }}</p>
      <div class="buttons">
        <button @click="onClickSubmit">Register</button>
        <router-link to="/login" class="button">Log in</router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";

@Options({})
export default class Login extends Vue {
  public passwordConfirm = "";
  public error = "";

  public credentials = {
    username: "",
    password: "",
    firstName: "",
    lastName: "",
  };

  public onClickSubmit(): void {
    session
      .register(this.credentials)
      .then(() => this.$router.push({ name: "login" }))
      .catch((data: Record<string, never>) => {
        this.error = data.error;
      });
  }
}
</script>

<style scoped lang="scss">
.error {
  color: red;
}
</style>
