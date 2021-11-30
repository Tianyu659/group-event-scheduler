<template>
  <div class="about">
    <h1>Settings</h1>
    <p>
      Hi, {{ session.user.firstName }}! You can use this page to block users and
      mark times you're planning on being unavailable.
    </p>
    <h2>Blocked Users</h2>
    <p v-if="session.user.blocked.length === 0">You haven't blocked anyone!</p>
    <ul v-else class="fancy">
      <li v-for="user of session.user.blocked" :key="user.id">
        <span>
          {{ user.firstName }} {{ user.lastName }} ({{ user.username }})
        </span>
        <span class="float-right cursor-pointer" @click="onClickDelete(user)">
          &#x2715;
        </span>
      </li>
    </ul>
    <profile-block-form />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import ProfileBlockForm from "@/views/ProfileBlockForm.vue";
import { BlockedUser } from "@/models/user";
import { url } from "@/url";

@Options({
  components: { ProfileBlockForm },
})
export default class Home extends Vue {
  public readonly session = session;

  public onClickDelete(user: BlockedUser): void {
    fetch(url(`/users/${this.session.user!.id}/blocks/${user.id}`), {
      method: "DELETE",
      headers: { Authorization: session.token! },
      body: JSON.stringify({ username: user.username }),
    }).then(() => {
      session.refresh();
    });
  }
}
</script>
