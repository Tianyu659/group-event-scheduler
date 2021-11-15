<template>
  <div class="form">
    <h4>Manual Event</h4>
    <div class="group">
      <input type="text" placeholder="event name" />
      <label>Event name</label>
    </div>
    <div class="group">
      <input type="text" placeholder="event description" />
      <label>Event description</label>
    </div>
    <div class="group">
      <input type="text" placeholder="event location" />
      <label>Event location</label>
    </div>
    <div class="group flex">
      <div>
        <input type="datetime-local" v-model="time" @input="test" />
        <label>Event time</label>
      </div>
      <div>
        <input type="number" />
        <label>Duration in minutes</label>
      </div>
    </div>
    <div class="group right">
      <button>Add event</button>
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
  public time = "";

  public created(): void {
    this.groupDateEvent = GroupDateEvent.empty(this.groupDate);
  }

  public test(): void {
    console.log(this.time);
  }
}
</script>

<style scoped></style>
