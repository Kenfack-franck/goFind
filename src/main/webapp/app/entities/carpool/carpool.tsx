import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table, Input } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './carpool.reducer';

const categories = ['origin', 'destination', 'departureTime', 'seatsAvailable', 'description', 'price'];
const statuses = ['active', 'completed', 'cancelled'];

export const Carpool = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [searchTerm, setSearchTerm] = useState('');
  const [categoryFilter, setCategoryFilter] = useState('');
  const [statusFilter, setStatusFilter] = useState('');

  const carpoolList = useAppSelector(state => state.carpool.entities);
  const loading = useAppSelector(state => state.carpool.loading);
  const totalItems = useAppSelector(state => state.carpool.totalItems);

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

  const handleCategoryFilter = event => {
    setCategoryFilter(event.target.value);
  };

  const handleStatusFilter = event => {
    setStatusFilter(event.target.value);
  };

  const filteredCarpools = carpoolList
    .filter(carpool => carpool.origin.toLowerCase().includes(searchTerm.toLowerCase()))
    .filter(carpool => (categoryFilter ? carpool.category === categoryFilter : true))
    .filter(carpool => (statusFilter ? carpool.status === statusFilter : true));

  return (
    <div>
      <h2 id="carpool-heading" data-cy="CarpoolHeading">
        <Translate contentKey="gofindApp.carpool.home.title">Carpools</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gofindApp.carpool.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/carpool/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gofindApp.carpool.home.createLabel">Create new Carpool</Translate>
          </Link>
        </div>
      </h2>
      <div className="mb-3 d-flex flex-wrap justify-content-between align-items-center">
        <Input
          type="text"
          name="search"
          value={searchTerm}
          onChange={handleSearch}
          placeholder="Search by origin"
          style={{ width: '25%', minWidth: '200px' }}
          className="mb-2"
        />
        <div className="d-flex justify-content-between" style={{ width: '50%', minWidth: '300px' }}>
          <Input
            type="select"
            name="category"
            value={categoryFilter}
            onChange={handleCategoryFilter}
            className="me-2"
            style={{ width: '45%' }}
          >
            <option value="">All Categories</option>
            {categories.map(category => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </Input>
          <Input type="select" name="status" value={statusFilter} onChange={handleStatusFilter} style={{ width: '45%' }}>
            <option value="">All Statuses</option>
            {statuses.map(status => (
              <option key={status} value={status}>
                {status}
              </option>
            ))}
          </Input>
        </div>
      </div>
      <div className="d-flex flex-wrap justify-content-between">
        {filteredCarpools && filteredCarpools.length > 0
          ? filteredCarpools.map((carpool, i) => (
              <div
                key={`entity-${i}`}
                className="card m-2 p-3"
                style={{ width: '300px', borderRadius: '10px', boxShadow: '0 2px 5px rgba(0,0,0,0.1)' }}
              >
                <div className="card-body">
                  <h5 className="card-title">
                    <Button tag={Link} to={`/carpool/${carpool.id}`} color="link" size="sm">
                      {carpool.id}
                    </Button>
                  </h5>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.carpool.origin">Origin:</Translate>
                    </strong>{' '}
                    {carpool.origin}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.carpool.destination">Destination:</Translate>
                    </strong>{' '}
                    {carpool.destination}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.carpool.departureTime">Departure Time:</Translate>
                    </strong>{' '}
                    {carpool.departureTime ? <TextFormat type="date" value={carpool.departureTime} format={APP_DATE_FORMAT} /> : null}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.carpool.seatsAvailable">Seats Available:</Translate>
                    </strong>{' '}
                    {carpool.seatsAvailable}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.carpool.description">Description:</Translate>
                    </strong>{' '}
                    {carpool.description}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.carpool.price">Price:</Translate>
                    </strong>{' '}
                    {carpool.price}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.carpool.driver">Driver:</Translate>
                    </strong>{' '}
                    {carpool.driver ? <Link to={`/utilisateur/${carpool.driver.id}`}>{carpool.driver.id}</Link> : ''}
                  </p>
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`/carpool/${carpool.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.view">View</Translate>
                      </span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`/carpool/${carpool.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                      color="primary"
                      size="sm"
                      data-cy="entityEditButton"
                    >
                      <FontAwesomeIcon icon="pencil-alt" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.edit">Edit</Translate>
                      </span>
                    </Button>
                    <Button
                      onClick={() =>
                        (window.location.href = `/carpool/${carpool.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                      }
                      color="danger"
                      size="sm"
                      data-cy="entityDeleteButton"
                    >
                      <FontAwesomeIcon icon="trash" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.delete">Delete</Translate>
                      </span>
                    </Button>
                  </div>
                </div>
              </div>
            ))
          : !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="gofindApp.carpool.home.notFound">No Carpools found</Translate>
              </div>
            )}
      </div>
      {totalItems ? (
        <div className={filteredCarpools && filteredCarpools.length > 0 ? '' : 'd-none'}>
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

export default Carpool;
