interface Event {
    id?: string;
    name?: string;
    traceId?: string;
    timestamp?: Date;
    payload?: string;
}

export default Event;