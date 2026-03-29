package com.eventhub.dto.ai;

import java.util.List;

public class EventScheduleResponse {

    private List<AgendaItemResponse> agendaItems;

    public EventScheduleResponse() {
    }

    public EventScheduleResponse(List<AgendaItemResponse> agendaItems) {
        this.agendaItems = agendaItems;
    }

    public List<AgendaItemResponse> getAgendaItems() {
        return agendaItems;
    }

    public void setAgendaItems(List<AgendaItemResponse> agendaItems) {
        this.agendaItems = agendaItems;
    }
}