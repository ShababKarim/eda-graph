'use client'
import {useEffect, useState} from "react";
import Event from '@/domain/event';
import PageRequest from '@/domain/page-request';
import EventSearchForm from "@/components/event/EventSearchForm";
import EventTable from "@/components/event/EventTable";

const DEFAULT_PAGE_SIZE = 10;

const initialPageRequest: PageRequest<Event> = {
    pageNumber: 0,
    pageSize: DEFAULT_PAGE_SIZE,
    example: {}
}

const EventMatrix = () => {
    const [pageRequest, setPageRequest] = useState<PageRequest<Event>>(initialPageRequest);
    const [events, setEvents] = useState<Event[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchEventsByExample = async () => {
            console.log(`Fetching events by example ${JSON.stringify(pageRequest)}...`)
            setLoading(true);
            await fetch(`${process.env.NEXT_PUBLIC_API_URL}/event/query`, {
                method: 'POST',
                mode: 'cors',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(pageRequest)
            })
                .then(res => res.json())
                .then(page => page as Page<Event>)
                .then(page => {
                    console.log(`Fetched events by example ${JSON.stringify(page)}`);
                    setEvents(page.content);
                })
                .catch(err => console.error(`Error fetching events by example with cause: [${err}]`))
            setLoading(false);
        }

        fetchEventsByExample();
    }, [pageRequest]);

    const updatePageRequest = (eventExample: Event) => {
        console.log(`Updating page request with event example ${JSON.stringify(eventExample)}...`)
        const newPageRequest = {
            ...pageRequest,
            example: {
                ...pageRequest.example,
                name: eventExample.name,
                traceId: eventExample.traceId
            }
        }
        setPageRequest(newPageRequest)
    }

    return (
        <>
            <EventSearchForm updatePageRequest={updatePageRequest}/>
            {loading
                ? <div>Loading...</div>
                : <EventTable events={events}/>
            }
        </>
    );
}

export default EventMatrix;