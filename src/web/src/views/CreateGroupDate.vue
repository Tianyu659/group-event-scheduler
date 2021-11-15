<template>
  <div>
    <h2>Create a Group Date</h2>
    <group-date-form @refresh="refresh()" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import GroupDateForm from "@/views/GroupDateForm.vue";

@Options({
  components: { GroupDateForm },
})
export default class Home extends Vue {
  public readonly session = session;

  public refresh(): void {
    console.log("refreshing!");
  }
}
</script>

<style scoped></style>
