<template>
  <div class="about">
    <h1>Settings</h1>
    <p>
      Hi, {{ session.user.firstName }}! You can use this page to block users and
      mark times you're planning on being unavailable.
    </p>
    <h2>Blocked Users</h2>
    <p>You haven't blocked anyone!</p>
    <profile-block-form />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import ProfileBlockForm from "@/views/ProfileBlockForm.vue";

@Options({
  components: { ProfileBlockForm },
})
export default class Home extends Vue {
  public readonly session = session;
}
</script>
