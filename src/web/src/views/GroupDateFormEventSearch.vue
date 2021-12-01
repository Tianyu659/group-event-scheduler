<template>
  <div class="form">
    <h4>Ticketmaster Events</h4>
    <div class="group">
      <input type="text" placeholder="Keyword" v-model="search.keyword" />
      <label>Search by keyword</label>
    </div>
    <div class="group">
      <input type="text" placeholder="00000" v-model="search.location" />
      <label>Search by zip code</label>
    </div>
    <div class="group">
      <select v-model="search.genre" style="width: 100%">
        <option value="">Any</option>
        <option value="KZFzniwnSyZfZ7v7nE">Sports</option>
        <option value="KZFzniwnSyZfZ7v7nJ">Music</option>
        <option value="KZFzniwnSyZfZ7v7na">Arts & Theater</option>
        <option value="KZFzniwnSyZfZ7v7nn">Film</option>
      </select>
      <label>Search by genre</label>
    </div>
    <div class="group flex">
      <div>
        <input type="date" v-model="search.startDate" />
        <label>Start date</label>
      </div>
      <div>
        <input type="date" v-model="search.endDate" />
        <label>End date</label>
      </div>
    </div>
    <div class="group">
      <button class="primary" @click="onClickSearch">Search events</button>
    </div>
    <ul class="fancy click">
      <li v-for="event of events" :key="event.eid" @click="onClickEvent(event)">
        {{ event.name }}: {{ event.location }}
      </li>
      <li v-show="events.length === 0">No events found!</li>
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
  public search = {
    keyword: "",
    location: "",
    startDate: "",
    endDate: "",
    genre: "",
  };
  public events: Array<GroupDateEvent> = [];

  public onClickSearch(): void {
    let query = [];
    if (this.search.keyword.trim().length > 0) {
      query.push("keyword=" + encodeURIComponent(this.search.keyword));
    }
    if (this.search.location.trim().length > 0) {
      query.push("postalCode=" + this.search.location);
    }
    if (this.search.genre.length > 0) {
      query.push("segmentId=" + this.search.genre);
    }
    console.log(this.search);
    if (this.search.startDate.length > 0) {
      query.push("startDateTime=" + this.search.startDate + "T00:00:00Z");
    }
    if (this.search.endDate.length > 0) {
      query.push("endDateTime=" + this.search.endDate + "T23:59:59Z");
    }

    fetch(url(`/ticketmaster/?${query.join("&")}`), {
      method: "POST",
      headers: { Authorization: session.token! },
    }).then((response: Response) => {
      response.json().then((data: Record<string, any>) => {
        this.events.length = 0;
        if (data["_embedded"] && data["_embedded"]["events"]) {
          this.events.push(
            ...data["_embedded"]["events"].map((data: any) =>
              GroupDateEvent.ticketmaster(data)
            )
          );
        }
      });
    });
  }

  public onClickEvent(event: GroupDateEvent): void {
    this.$emit("submit", event);
  }
}
</script>

<style scoped></style>
