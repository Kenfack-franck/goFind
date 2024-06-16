import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Input, Table } from 'reactstrap';
import { Translate, TextFormat, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from './item.reducer';

const categories = ['telephone', 'laptop', 'souris', 'clavier', 'cle_usb', 'chargeur', 'bafle'];
const statuses = ['a vendre', 'voler', 'retrouve'];

export const Item = () => {
  const dispatch = useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState({
    activePage: 1,
    itemsPerPage: ITEMS_PER_PAGE,
    sort: 'id',
    order: ASC,
  });
  const [searchTerm, setSearchTerm] = useState('');
  const [categoryFilter, setCategoryFilter] = useState('');
  const [statusFilter, setStatusFilter] = useState('');
  const [filteredItems, setFilteredItems] = useState([]);

  const itemList = useAppSelector(state => state.item.entities);
  const loading = useAppSelector(state => state.item.loading);
  const totalItems = useAppSelector(state => state.item.totalItems);

  useEffect(() => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  }, [paginationState]);

  useEffect(() => {
    setFilteredItems(itemList);
  }, [itemList]);

  const sortEntities = () => {
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
  };

  const handlePagination = currentPage => {
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });
  };

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSearch = event => {
    const term = event.target.value;
    setSearchTerm(term);
    filterItems(term, categoryFilter, statusFilter);
  };

  const handleCategoryFilter = event => {
    const category = event.target.value;
    setCategoryFilter(category);
    filterItems(searchTerm, category, statusFilter);
  };

  const handleStatusFilter = event => {
    const status = event.target.value;
    setStatusFilter(status);
    filterItems(searchTerm, categoryFilter, status);
  };

  const filterItems = (term, category, status) => {
    let updatedList = itemList;
    if (term) {
      updatedList = updatedList.filter(item => item.name.toLowerCase().includes(term.toLowerCase()));
    }
    if (category) {
      updatedList = updatedList.filter(item => item.category === category);
    }
    if (status) {
      updatedList = updatedList.filter(item => item.status === status);
    }
    setFilteredItems(updatedList);
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="item-heading" data-cy="ItemHeading">
        <Translate contentKey="gofindApp.item.home.title">Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={sortEntities} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gofindApp.item.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/item/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gofindApp.item.home.createLabel">Create new Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="mb-3 d-flex flex-wrap justify-content-between align-items-center">
        <Input
          type="text"
          name="search"
          value={searchTerm}
          onChange={handleSearch}
          placeholder="Search by name"
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
        {filteredItems && filteredItems.length > 0
          ? filteredItems.map((item, i) => (
              <div
                key={`entity-${i}`}
                className="card m-2 p-3"
                style={{ width: '300px', borderRadius: '10px', boxShadow: '0 2px 5px rgba(0,0,0,0.1)' }}
              >
                <div className="card-body">
                  {/* <h5 className="card-title">
                    <Button tag={Link} to={`/item/${item.id}`} color="link" size="sm">
                      {item.id}
                    </Button>
                  </h5> */}
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.item.name">Name:</Translate>
                    </strong>{' '}
                    {item.name}
                  </p>
                  {/* <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.item.description">Description:</Translate>
                    </strong>{' '}
                    {item.description}
                  </p> */}
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.item.category">Category:</Translate>
                    </strong>{' '}
                    {item.category}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.item.status">Status:</Translate>
                    </strong>{' '}
                    {item.status}
                  </p>
                  {/* <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.item.creationDate">Creation Date:</Translate>
                    </strong>{' '}
                    {item.creationDate ? <TextFormat type="date" value={item.creationDate} format={APP_DATE_FORMAT} /> : null}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.item.owner">Owner:</Translate>
                    </strong>{' '}
                    {item.owner ? <Link to={`/utilisateur/${item.owner.id}`}>{item.owner.id}</Link> : ''}
                  </p> */}
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`/item/${item.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.view">View</Translate>
                      </span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`/item/${item.id}/edit?page={paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                      color="primary"
                      size="sm"
                      data-cy="entityEditButton"
                    >
                      <FontAwesomeIcon icon="pencil-alt" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.edit">Edit1</Translate>
                      </span>
                    </Button>
                    <Button
                      onClick={() =>
                        (window.location.href = `/item/${item.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                      }
                      color="info"
                      size="sm"
                      data-cy="entityDeleteButton"
                    >
                      {/* <FontAwesomeIcon icon="trash" />{' '} */}
                      <span className="d-none d-md-inline">ACHETER</span>
                    </Button>
                  </div>
                </div>
              </div>
            ))
          : !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="gofindApp.item.home.notFound">No Items found</Translate>
              </div>
            )}
      </div>
      {totalItems ? (
        <div className={filteredItems && filteredItems.length > 0 ? '' : 'd-none'}>
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

export default Item;
