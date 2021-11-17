<template>
  <div>
    <h2>Hello</h2>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";

@Options({})
export default class Home extends Vue {
  public readonly session = session;

  public created(): void {
    console.log(this.$route.params["id"]);
  }

  public refresh(): void {
    console.log("refreshing!");
  }
}
</script>

<style scoped></style>
