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
        <span
          class="float-right cursor-pointer"
          @click="onClickDeleteBlocked(user)"
        >
          &#x2715;
        </span>
      </li>
    </ul>
    <profile-block-form />
    <h2>Unavailable Dates</h2>
    <p v-if="session.user.blackouts.length === 0">
      You don't have any unavailable dates.
    </p>
    <ul v-else class="fancy">
      <li v-for="blackout of session.user.blackouts" :key="blackout.id">
        <span>
          {{ formatDate(blackout.start) }} - {{ formatDate(blackout.end) }}
        </span>
        <span
          class="float-right cursor-pointer"
          @click="onClickDeleteBlackout(blackout)"
        >
          &#x2715;
        </span>
      </li>
    </ul>
    <profile-blackout-form />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import ProfileBlockForm from "@/views/ProfileBlockForm.vue";
import { Blackout, BlockedUser } from "@/models/user";
import { url } from "@/url";
import ProfileBlackoutForm from "@/views/ProfileBlackoutForm.vue";
import { formatDateTimeShort } from "@/common/date";

@Options({
  components: { ProfileBlackoutForm, ProfileBlockForm },
})
export default class Home extends Vue {
  public readonly session = session;
  public readonly formatDate = formatDateTimeShort;

  public onClickDeleteBlocked(user: BlockedUser): void {
    fetch(url(`/users/${this.session.user!.id}/blocks/${user.id}`), {
      method: "DELETE",
      headers: { Authorization: session.token! },
    }).then(() => {
      session.refresh();
    });
  }

  public onClickDeleteBlackout(blackout: Blackout): void {
    fetch(url(`/users/${this.session.user!.id}/blackouts/${blackout.id}`), {
      method: "DELETE",
      headers: { Authorization: session.token! },
    }).then(() => {
      session.refresh();
    });
  }
}
</script>
