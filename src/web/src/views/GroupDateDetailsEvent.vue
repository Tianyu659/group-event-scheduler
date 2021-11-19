<template>
  <div class="event">
    <h4>{{ groupDateEvent.name }}</h4>
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
import { GroupDateEvent } from "@/models/groupDate";
import { formatDateTime } from "@/common/date";

@Options({})
export default class GroupDateDetailsEvent extends Vue {
  @Prop() public groupDateEvent!: GroupDateEvent;
  public readonly formatDateTime = formatDateTime;
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
