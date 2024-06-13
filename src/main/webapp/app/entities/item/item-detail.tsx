import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './item.reducer';

export const ItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const itemEntity = useAppSelector(state => state.item.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemDetailsHeading">
          <Translate contentKey="gofindApp.item.detail.title">Item</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="gofindApp.item.name">Name</Translate>
            </span>
          </dt>
          <dd>{itemEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gofindApp.item.description">Description</Translate>
            </span>
          </dt>
          <dd>{itemEntity.description}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="gofindApp.item.category">Category</Translate>
            </span>
          </dt>
          <dd>{itemEntity.category}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="gofindApp.item.status">Status</Translate>
            </span>
          </dt>
          <dd>{itemEntity.status}</dd>
          <dt>
            <span id="creationDate">
              <Translate contentKey="gofindApp.item.creationDate">Creation Date</Translate>
            </span>
          </dt>
          <dd>{itemEntity.creationDate ? <TextFormat value={itemEntity.creationDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="gofindApp.item.owner">Owner</Translate>
          </dt>
          <dd>{itemEntity.owner ? itemEntity.owner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item/${itemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemDetail;
