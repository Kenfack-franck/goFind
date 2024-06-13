import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rental.reducer';

export const RentalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rentalEntity = useAppSelector(state => state.rental.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rentalDetailsHeading">
          <Translate contentKey="gofindApp.rental.detail.title">Rental</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.id}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="gofindApp.rental.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.startDate ? <TextFormat value={rentalEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="gofindApp.rental.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.endDate ? <TextFormat value={rentalEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="gofindApp.rental.status">Status</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.status}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="gofindApp.rental.price">Price</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.price}</dd>
          <dt>
            <Translate contentKey="gofindApp.rental.renter">Renter</Translate>
          </dt>
          <dd>{rentalEntity.renter ? rentalEntity.renter.id : ''}</dd>
          <dt>
            <Translate contentKey="gofindApp.rental.property">Property</Translate>
          </dt>
          <dd>{rentalEntity.property ? rentalEntity.property.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rental" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rental/${rentalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RentalDetail;
