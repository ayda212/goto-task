import React,{useState,useEffect,useCallback} from 'react';
import './SearchComponent.css';

const SearchComponent = ()=>{
    const [customerId, setCustomerId] = useState('');
    const [typeName, settypeName] = useState('');
    const [searchQuery, setSearchQuery] = useState('');
    const [results, setResults] = useState([]);
    const [pageInfo, setPageInfo] = useState({});
    const [error, setError] = useState(null);

    const fetchData = useCallback(async (page = 0) => {
        try {
            const response = await fetch(`http://localhost:8080/payment?customerId=${customerId}&typeName=${typeName}&page=${page}&size=3`);
            if(!response.ok){
                throw new Error('network response was not ok');
            }
            const data = await response.json();
            setResults(data.content);
            setPageInfo(data.pageable);
        } catch (error) {
            setError(error);
        }
    },[customerId,typeName]);

    useEffect(()=>{
        fetchData();
    }, [fetchData]);

    const handleSearch = (e) => {
        setSearchQuery(e.target.value);
    }

    const filteredResult = results.filter((payment) => {
        const isMatchingSearch = payment.amount.toString().toLowerCase().includes(searchQuery.toLowerCase());
        return isMatchingSearch;
    });

    return (
        <div className='centered'>
            <h1>Search and Filter payments</h1>
            <div>
                <input
                type='text'
                placeholder='search by amount'
                value={searchQuery}
                onChange={handleSearch}></input>

                <input
                type='text'
                placeholder='customer Id'
                value={customerId}
                onChange={(e)=> setCustomerId(e.target.value)}>
                </input>

                <input
                type='text'
                placeholder='type name'
                value={typeName}
                onChange={(e)=> settypeName(e.target.value)}>
                </input>
            </div>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>payment Id</th>
                            <th>amount</th>
                            <th>type name</th>
                            <th>customer Id</th>
                        </tr>
                    </thead>
                    <tbody>
                        {error && <tr><td colSpan="4">Error: {error.message}</td></tr>}
                        {filteredResult.map((payment)=>(
                            <tr key={payment.paymentId}>
                                <td>{payment.paymentId}</td>
                                <td>{payment.amount}</td>
                                <td>{payment.paymentType.typeName}</td>
                                <td>{payment.customerid}</td>
                            </tr>
                        ))}
                        {results.length === 0 && <tr><td colSpan="4">no result found</td></tr>}
                    </tbody>
                </table>
            </div>

            <div>
                <button
                disabled={pageInfo.pageNumber === 0}
                onClick={()=> fetchData(pageInfo.pageNumber-1)}
                >
                    previous page
                </button>

                <button
                disabled={pageInfo.last}
                onClick={()=> fetchData(pageInfo.pageNumber+1)}
                >
                    next page
                </button>
            </div>

        </div>
    );
};
export default SearchComponent;