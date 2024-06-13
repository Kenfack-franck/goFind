import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './property.reducer';

export const PropertyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const propertyEntity = useAppSelector(state => state.property.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="propertyDetailsHeading">
          <Translate contentKey="gofindApp.property.detail.title">Property</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.id}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="gofindApp.property.location">Location</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.location}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gofindApp.property.description">Description</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.description}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="gofindApp.property.price">Price</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.price}</dd>
          <dt>
            <span id="availabilityStatus">
              <Translate contentKey="gofindApp.property.availabilityStatus">Availability Status</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.availabilityStatus}</dd>
          <dt>
            <span id="propertySize">
              <Translate contentKey="gofindApp.property.propertySize">Property Size</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.propertySize}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="gofindApp.property.type">Type</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.type}</dd>
          <dt>
            <Translate contentKey="gofindApp.property.owner">Owner</Translate>
          </dt>
          <dd>{propertyEntity.owner ? propertyEntity.owner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/property" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/property/${propertyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PropertyDetail;
