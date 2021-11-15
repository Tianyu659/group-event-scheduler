<template>
  <div class="form">
    <div class="group">
      <input type="text" placeholder="event name" />
      <label>Event name</label>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import { GroupDateEvent } from "@/models/groupDate";

@Options({})
export default class GroupDateFormEvent extends Vue {
  @Prop() public groupDateEvent!: GroupDateEvent;
}
</script>

<style scoped></style>
