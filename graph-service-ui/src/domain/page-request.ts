interface PageRequest<T> {
    pageNumber: number;
    pageSize: number;
    example: T | null
}

export default PageRequest;