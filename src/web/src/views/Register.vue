<template>
  <div class="small">
    <h1>Register</h1>
    <form>
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
          v-model="credentials.passwordConfirm"
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
          v-model="credentials.username"
        />
        <label>Last name</label>
      </div>
      <div class="buttons">
        <button @click="onClickSubmit">Register</button>
        <router-link to="/login" class="button">Log in</router-link>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";

@Options({})
export default class Login extends Vue {
  public credentials = {
    username: "",
    password: "",
    passwordConfirm: "",
    firstName: "",
    lastName: "",
  };

  public onClickSubmit(): void {
    console.log("log in!");
  }
}
</script>

<style scoped lang="scss"></style>
