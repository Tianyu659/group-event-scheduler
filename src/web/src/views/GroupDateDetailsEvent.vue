<template>
  <div class="event" :class="{ best: best }">
    <h4>
      <span>{{ groupDateEvent.name }}</span>
      <span class="float-right cursor-pointer" @click="onClickDelete">
        &#x2715;
      </span>
    </h4>
    <p class="location">
      {{ groupDateEvent.location }}<br />
      {{ formatDateTime(groupDateEvent.time) }}<br />
      {{ groupDateEvent.availableCount }}/{{
        groupDateEvent.interest.length
      }}
      available<span v-if="groupDateEvent.availableCount > 0"
        >, {{ groupDateEvent.averageInterest }} interest
      </span>
    </p>
    <p>{{ groupDateEvent.description || "No description provided" }}</p>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import { GroupDate, GroupDateEvent } from "@/models/groupDate";
import { formatDateTime } from "@/common/date";
import { url } from "@/url";
import { session } from "@/session";
import router from "@/router";

@Options({})
export default class GroupDateDetailsEvent extends Vue {
  @Prop() public groupDate!: GroupDate;
  @Prop() public groupDateEvent!: GroupDateEvent;
  @Prop() public best!: boolean;
  public readonly formatDateTime = formatDateTime;

  public onClickDelete(): void {
    if (this.groupDate.events.length === 1) {
      const result = window.confirm(
        "This is the last event. Do you wish to delete the group date?"
      );
      if (result) {
        fetch(url(`/groupDates/${this.groupDate.id}`), {
          method: "DELETE",
          headers: { Authorization: session.token! },
        }).then((response: Response) => {
          if (response.status === 204) {
            router.push({ name: "dashboard" });
          }
        });
      }
    } else {
      const result = window.confirm(
        `Delete event ${this.groupDateEvent.name}?`
      );
      if (result) {
        fetch(
          url(
            `/groupDates/${this.groupDate.id}/events/${this.groupDateEvent.id}`
          ),
          {
            method: "DELETE",
            headers: { Authorization: session.token! },
          }
        ).then((response: Response) => {
          if (response.status === 204) {
            this.$emit("delete");
          }
        });
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.event {
  border: 1px solid lightgray;
  padding: 1em;
  border-radius: 0.25rem;

  .location {
    font-weight: bold;
  }

  & > *:first-child {
    margin-top: 0;
  }

  & > *:last-child {
    margin-bottom: 0;
  }

  margin-bottom: 1rem;
}
</style>
