<template>
  <div class="form">
    <h4>Add Dates</h4>
    <div class="group flex">
      <div>
        <input type="date" v-model="start" />
        <label>Start date</label>
      </div>
      <div>
        <input type="date" v-model="end" />
        <label>End date</label>
      </div>
    </div>
    <div class="group">
      <button class="primary" @click="onClickSubmit">Create</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { url } from "@/url";
import { User } from "@/models/user";

@Options({})
export default class Home extends Vue {
  public readonly session = session;
  public start = "";
  public end = "";

  public onClickSubmit(): void {
    fetch(url(`/users/${this.session.user!.id}/blackouts/`), {
      method: "POST",
      headers: { Authorization: session.token! },
      body: JSON.stringify({
        start: new Date(this.start).getTime() / 1000,
        end: new Date(this.end).getTime() / 1000,
      }),
    }).then(() => {
      this.start = "";
      this.end = "";
      session.refresh();
    });
  }
}
</script>
