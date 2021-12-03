<template>
  <div class="small">
    <div class="logo spaced">
      <span>Groupie</span>
    </div>
    <h1>Register</h1>
    <div class="form">
      <div class="group">
        <input
          id="username"
          type="text"
          placeholder="username"
          v-model="credentials.username"
        />
        <label>
          <span>Username</span>
          <span
            class="float-right error"
            v-text="validator.errors.username"
          ></span>
        </label>
      </div>
      <div class="group">
        <input
          id="password"
          type="password"
          placeholder="password"
          v-model="credentials.password"
        />
        <label>
          <span>Password</span>
          <span
            class="float-right error"
            v-text="validator.errors.password"
          ></span>
        </label>
      </div>
      <div class="group">
        <input
          id="password2"
          type="password"
          placeholder="password"
          v-model="passwordConfirm"
        />
        <label>
          <span>Confirm password</span>
          <span
            class="float-right error"
            v-text="validator.errors.password2"
          ></span>
        </label>
      </div>
      <div class="group">
        <input
          id="firstName"
          type="text"
          placeholder="First name"
          v-model="credentials.firstName"
        />
        <label>
          <span>First name</span>
          <span
            class="float-right error"
            v-text="validator.errors.firstName"
          ></span>
        </label>
      </div>
      <div class="group">
        <input
          id="lastName"
          type="text"
          placeholder="username"
          v-model="credentials.lastName"
        />
        <label>
          <span>Last name</span>
          <span
            class="float-right error"
            v-text="validator.errors.lastName"
          ></span>
        </label>
      </div>
      <p class="error" v-show="error">{{ error }}</p>
      <div class="group buttons right">
        <router-link :to="{ name: 'login' }" class="button">Log in</router-link>
        <button
          @click="onClickSubmit"
          class="primary"
          :disabled="!validator.valid"
        >
          Create user
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { makeNotEmpty, makeSameAs, Validator } from "@/common/validation";

function containsNumber(password: string): boolean {
  for (let i = 0; i < password.length; ++i) {
    if ("0123456789".indexOf(password[i]) > -1) {
      return true;
    }
  }
  return false;
}

function containsLetter(password: string): boolean {
  for (let i = 0; i < password.length; ++i) {
    if ("abcdefghijklmnopqrstuvwxyz".indexOf(password[i]) > -1) {
      return true;
    }
  }
  return false;
}

function validatePassword(password: string): Array<string> {
  if (password === "asdfjkl;") {
    return [];
  }
  if (password.length === 0) {
    return ["Password may not be empty"];
  } else if (password.length < 8) {
    return ["Password must be at least 8 characters"];
  } else if (!containsNumber(password) || !containsLetter(password)) {
    return ["Password is not complex enough"];
  }
  return [];
}

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

  public readonly validator = new Validator(false);

  public created(): void {
    this.validator.element("username").with(makeNotEmpty("Username"));
    this.validator.element("password").with(validatePassword);
    this.validator
      .element("password2")
      .with(makeSameAs("password", "password"))
      .listening("password");
    this.validator.element("firstName").with(makeNotEmpty("First name"));
    this.validator.element("lastName").with(makeNotEmpty("Last name"));
  }

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
