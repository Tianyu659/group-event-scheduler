<template>
  <div class="form">
    <h4>Manual Event</h4>
    <div class="group">
      <input type="text" placeholder="event name" />
      <label>Event name</label>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { GroupDate, GroupDateEvent } from "@/models/groupDate";
import { Prop } from "vue-property-decorator";

@Options({})
export default class GroupDateForm extends Vue {
  @Prop() groupDate!: GroupDate;
  public groupDateEvent!: GroupDateEvent;

  public created(): void {
    this.groupDateEvent = GroupDateEvent.empty(this.groupDate);
  }
}
</script>

<style scoped></style>
