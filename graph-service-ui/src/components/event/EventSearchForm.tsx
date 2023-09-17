'use client'
import {useState} from "react";
import Event from '@/domain/event';

const setNullIfBlank = (value: string) => value?.trim().length === 0 ? null : value;

const getEventExampleWithDefaults = (event: any) => {
    return Object.keys(event).reduce((res, key) => ({...res, [key]: setNullIfBlank(event[key])}), {}) as Event
}

const EventSearchForm = ({updatePageRequest}: { updatePageRequest: (search: Event) => void }) => {
    const [example, setExample] = useState<Event>({
        name: '',
        traceId: ''
    });

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()

        updatePageRequest(getEventExampleWithDefaults(example))
    }

    const handleSearchChange = (event: React.FormEvent<HTMLInputElement>) => {
        setExample({...example, [event.currentTarget.name]: event.currentTarget.value})
    }

    return (
        <form onSubmit={handleSubmit} className='flex justify-center mb-6'>
            <input type='text' name='name' value={example.name} onChange={handleSearchChange} placeholder='Event Name'
                   className='pl-1 mb-2 text-sm border border-black-500 rounded-lg focus:border-black-300'/>
            <input type='text' name='traceId' value={example.traceId} onChange={handleSearchChange}
                   placeholder='Trace ID'
                   className='ml-2 pl-1 mb-2 text-sm border border-black-500 rounded-lg hover:border-black-300'/>
            <button type='submit'
                    className='ml-2 text-white bg-blue-500 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 focus:outline-none'>Search
            </button>
        </form>
    )
}

export default EventSearchForm;