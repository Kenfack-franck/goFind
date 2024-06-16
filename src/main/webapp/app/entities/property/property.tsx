import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, FormGroup, Label } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './property.reducer';

export const Property = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [searchTerm, setSearchTerm] = useState('');
  const [filterType, setFilterType] = useState('');

  const propertyList = useAppSelector(state => state.property.entities);
  const loading = useAppSelector(state => state.property.loading);
  const totalItems = useAppSelector(state => state.property.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = fieldName => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  const handleSearch = event => {
    setSearchTerm(event.target.value);
  };

  const handleFilterTypeChange = event => {
    setFilterType(event.target.value);
  };

  const filteredProperties = propertyList.filter(property => {
    return property.location.toLowerCase().includes(searchTerm.toLowerCase()) && (filterType ? property.type === filterType : true);
  });

  return (
    <div>
      <h2 id="property-heading" data-cy="PropertyHeading">
        <Translate contentKey="gofindApp.property.home.title">Properties</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gofindApp.property.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/property/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gofindApp.property.home.createLabel">Create new Property</Translate>
          </Link>
        </div>
      </h2>
      <div className="mb-3 d-flex flex-wrap justify-content-between align-items-center">
        <Input
          type="text"
          name="search"
          value={searchTerm}
          onChange={handleSearch}
          placeholder="Search by location"
          style={{ width: '30%', minWidth: '200px' }}
          className="mb-2"
        />
        <FormGroup className="mb-2" style={{ width: '30%', minWidth: '200px' }}>
          <Input type="select" name="filterType" value={filterType} onChange={handleFilterTypeChange}>
            <option value="">All Types</option>
            <option value="chambre simple">Chambre Simple</option>
            <option value="chambre moderne">Chambre Moderne</option>
            <option value="apartement">Apartement</option>
            <option value="villa">Villa</option>
          </Input>
        </FormGroup>
      </div>
      <div className="d-flex flex-wrap justify-content-between">
        {filteredProperties && filteredProperties.length > 0
          ? filteredProperties.map((property, i) => (
              <div
                key={`entity-${i}`}
                className="card m-2 p-3"
                style={{ width: '300px', borderRadius: '10px', boxShadow: '0 2px 5px rgba(0,0,0,0.1)' }}
              >
                <div className="card-body">
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.property.location">Location:</Translate>
                    </strong>{' '}
                    {property.location}
                  </p>
                  {/* <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.property.description">Description:</Translate>
                    </strong>{' '}
                    {property.description}
                  </p> */}
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.property.price">Price:</Translate>
                    </strong>{' '}
                    {property.price}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.property.availabilityStatus">Availability Status:</Translate>
                    </strong>{' '}
                    {property.availabilityStatus}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.property.propertySize">Property Size:</Translate>
                    </strong>{' '}
                    {property.propertySize}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.property.type">Type:</Translate>
                    </strong>{' '}
                    {property.type}
                  </p>
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`/property/${property.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.view">View</Translate>
                      </span>
                    </Button>
                    {/* <Button
                      tag={Link}
                      to={`/property/${property.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                      color="primary"
                      size="sm"
                      data-cy="entityEditButton"
                    >
                      <FontAwesomeIcon icon="pencil-alt" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.edit">Edit</Translate>
                      </span>
                    </Button> */}
                    <Button
                      onClick={() =>
                        (window.location.href = `/property/${property.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                      }
                      color="primary"
                      size="sm"
                      data-cy="entityDeleteButton"
                    >
                      {/* <FontAwesomeIcon icon="trash" />{' '} */}
                      <span className="d-none d-md-inline">
                        {/* <Translate contentKey="entity.action.delete">Delete</Translate> */}
                        souscription
                      </span>
                    </Button>
                  </div>
                </div>
              </div>
            ))
          : !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="gofindApp.property.home.notFound">No Properties found</Translate>
              </div>
            )}
      </div>
      {totalItems ? (
        <div className={propertyList && propertyList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Property;
