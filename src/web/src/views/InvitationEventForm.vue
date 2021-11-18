<template>
  <div class="event form">
    <h4>{{ invitationEventResponse.event.name }}</h4>
    <p class="location">
      {{ invitationEventResponse.event.location }}<br />
      {{ formatDateTime(invitationEventResponse.event.time) }}<br />
      {{ invitationEventResponse.event.availableCount }}/{{
        invitationEventResponse.event.interest.length
      }}
      available<span v-if="invitationEventResponse.event.availableCount > 0"
        >, {{ invitationEventResponse.event.averageInterest }} interest
      </span>
    </p>
    <p>
      {{
        invitationEventResponse.event.description || "No description provided"
      }}
    </p>
    <div class="group flex">
      <div>
        <select style="width: 100%" v-model="invitationEventResponse.available">
          <option :value="true">Available</option>
          <option :value="false">Unavailable</option>
        </select>
        <label>Available</label>
      </div>
      <div v-show="invitationEventResponse.available">
        <input
          type="number"
          min="1"
          max="5"
          v-model="invitationEventResponse.interest"
        />
        <label>Interest</label>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import { GroupDate, InvitationEventResponse } from "@/models/groupDate";
import { formatDateTime } from "@/common/date";

@Options({})
export default class GroupDateFormEvent extends Vue {
  @Prop() public invitationEventResponse!: InvitationEventResponse;
  @Prop() public groupDate!: GroupDate;
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
