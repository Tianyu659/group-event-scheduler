<template>
  <div class="form">
    <h4>Manual Event</h4>
    <div class="group">
      <input type="text" placeholder="event name" v-model="name" />
      <label>Event name</label>
    </div>
    <div class="group">
      <input
        type="text"
        placeholder="event description"
        v-model="description"
      />
      <label>Event description</label>
    </div>
    <div class="group">
      <input type="text" placeholder="event location" v-model="location" />
      <label>Event location</label>
    </div>
    <div class="group flex">
      <div>
        <input type="datetime-local" v-model="time" />
        <label>Event time</label>
      </div>
      <div>
        <input type="number" v-model="duration" />
        <label>Duration in minutes</label>
      </div>
    </div>
    <div class="group right">
      <button @click="onClickAdd">Add event</button>
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
  public name = "";
  public description = "";
  public location = "";
  public duration = 60;
  public time = "";

  public onClickAdd(): void {
    this.$emit(
      "submit",
      new GroupDateEvent(
        0,
        "",
        "",
        this.name,
        this.description,
        this.location,
        new Date(this.time),
        this.duration
      )
    );
  }
}
</script>

<style scoped></style>
