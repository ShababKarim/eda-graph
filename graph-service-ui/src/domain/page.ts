interface Page<T> {
    content: T[];
    pageable: {
        pageNumber: number;
        pageSize: number;
        sort: {
            empty: boolean;
            unsorted: boolean;
            sorted: boolean;
        };
        offset: number;
        paged: boolean;
        unpaged: boolean
    };
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number; // # of elems in page
    number: number; // current page #
    sort: {
        empty: boolean;
        unsorted: boolean;
        sorted: boolean;
    };
    numberOfElements: number;
    first: boolean;
    empty: boolean;
};