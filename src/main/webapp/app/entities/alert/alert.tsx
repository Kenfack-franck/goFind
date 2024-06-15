import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './alert.reducer';

export const Alert = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const alertList = useAppSelector(state => state.alert.entities);
  const loading = useAppSelector(state => state.alert.loading);
  const totalItems = useAppSelector(state => state.alert.totalItems);

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
      <h2 id="alert-heading" data-cy="AlertHeading">
        <Translate contentKey="gofindApp.alert.home.title">Alerts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="gofindApp.alert.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/alert/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="gofindApp.alert.home.createLabel">Create new Alert</Translate>
          </Link>
        </div>
      </h2>
      <div className="d-flex flex-wrap justify-content-between">
        {alertList && alertList.length > 0
          ? alertList.map((alert, i) => (
              <div
                key={`entity-${i}`}
                className="card m-2 p-3"
                style={{ width: '300px', borderRadius: '10px', boxShadow: '0 2px 5px rgba(0,0,0,0.1)' }}
              >
                <div className="card-body">
                  <h5 className="card-title">
                    <Button tag={Link} to={`/alert/${alert.id}`} color="link" size="sm">
                      {alert.id}
                    </Button>
                  </h5>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.alert.message">Message:</Translate>
                    </strong>{' '}
                    {alert.message}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.alert.date">Date:</Translate>
                    </strong>{' '}
                    {alert.date ? <TextFormat type="date" value={alert.date} format={APP_DATE_FORMAT} /> : null}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.alert.read">Read:</Translate>
                    </strong>{' '}
                    {alert.read ? 'true' : 'false'}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.alert.item">Item:</Translate>
                    </strong>{' '}
                    {alert.item ? <Link to={`/item/${alert.item.id}`}>{alert.item.id}</Link> : ''}
                  </p>
                  <p className="card-text">
                    <strong>
                      <Translate contentKey="gofindApp.alert.utilisateur">Utilisateur:</Translate>
                    </strong>{' '}
                    {alert.utilisateur ? <Link to={`/utilisateur/${alert.utilisateur.id}`}>{alert.utilisateur.id}</Link> : ''}
                  </p>
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`/alert/${alert.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.view">View</Translate>
                      </span>
                    </Button>
                    <Button
                      tag={Link}
                      to={`/alert/${alert.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        (window.location.href = `/alert/${alert.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
                <Translate contentKey="gofindApp.alert.home.notFound">No Alerts found</Translate>
              </div>
            )}
      </div>
      {totalItems ? (
        <div className={alertList && alertList.length > 0 ? '' : 'd-none'}>
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

export default Alert;
