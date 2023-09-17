import Event from '@/domain/event';

const EventTable = ({events}: {events: Event[]}) => {
    return (
        <div className='flex justify-center ml-1 overflow-x-auto'>
            <table className='w-full text-base text-left text-gray-500'>
                <thead className='text-sm text-gray-700 uppercase bg-gray-50'>
                    <tr>
                        <th className='py-3'>Name</th>
                        <th className='py-3'>Trace ID</th>
                        <th className='py-3'>Timestamp</th>
                        <th className='py-3'>Payload</th>
                    </tr>
                </thead>
                <tbody>
                    {events?.map(event => (
                        <tr key={event.id} className='bg-white border-b'>
                            <td className='font-medium py-4'>{event.name}</td>
                            <td className='py-4'>{event.traceId}</td>
                            <td className='py-4'>{event.timestamp?.toString()}</td>
                            <td className='py-4'>{event.payload}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default EventTable;