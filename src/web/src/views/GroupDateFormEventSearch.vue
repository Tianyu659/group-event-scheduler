<template>
  <div class="form">
    <h4>Ticketmaster Events</h4>
    <div class="group">
      <input type="text" placeholder="search" v-model="search" />
      <label>Search for events</label>
    </div>
    <div class="group">
      <button class="primary" @click="onClickSearch">Search by keyword</button>
    </div>
    <ul class="fancy click" v-show="events.length > 0">
      <li v-for="event of events" :key="event.eid" @click="onClickEvent(event)">
        {{ event.name }}: {{ event.location }}
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { GroupDate, GroupDateEvent } from "@/models/groupDate";
import { Prop } from "vue-property-decorator";
import { url } from "@/url";
import { session } from "@/session";

@Options({})
export default class GroupDateForm extends Vue {
  @Prop() groupDate!: GroupDate;
  public search = "";
  public events: Array<GroupDateEvent> = [];

  public onClickSearch(): void {
    const keyword = this.search.split(" ");
    fetch(url(`/ticketmaster/?keyword=${keyword.join(",")}`), {
      method: "POST",
      headers: { Authorization: session.token! },
    }).then((response: Response) => {
      response.json().then((data: Record<string, any>) => {
        this.events.length = 0;
        this.events.push(
          ...data["_embedded"]["events"].map((data: any) =>
            GroupDateEvent.ticketmaster(this.groupDate, data)
          )
        );
      });
    });
  }

  public onClickEvent(event: GroupDateEvent): void {
    this.$emit("submit", event);
  }
}
</script>

<style scoped></style>
