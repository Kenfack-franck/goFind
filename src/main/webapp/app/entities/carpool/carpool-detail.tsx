import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './carpool.reducer';

export const CarpoolDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const carpoolEntity = useAppSelector(state => state.carpool.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="carpoolDetailsHeading">
          <Translate contentKey="gofindApp.carpool.detail.title">Carpool</Translate>
        </h2>
        <dl className="jh-entity-details">
          {/* <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt> */}
          <dd>{carpoolEntity.id}</dd>
          <dt>
            <span id="origin">
              <Translate contentKey="gofindApp.carpool.origin">Origin</Translate>
            </span>
          </dt>
          <dd>{carpoolEntity.origin}</dd>
          <dt>
            <span id="destination">
              <Translate contentKey="gofindApp.carpool.destination">Destination</Translate>
            </span>
          </dt>
          <dd>{carpoolEntity.destination}</dd>
          <dt>
            <span id="departureTime">
              <Translate contentKey="gofindApp.carpool.departureTime">Departure Time</Translate>
            </span>
          </dt>
          <dd>
            {carpoolEntity.departureTime ? <TextFormat value={carpoolEntity.departureTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="seatsAvailable">
              <Translate contentKey="gofindApp.carpool.seatsAvailable">Seats Available</Translate>
            </span>
          </dt>
          <dd>{carpoolEntity.seatsAvailable}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gofindApp.carpool.description">Description</Translate>
            </span>
          </dt>
          <dd>{carpoolEntity.description}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="gofindApp.carpool.price">Price</Translate>
            </span>
          </dt>
          <dd>{carpoolEntity.price}</dd>
          <dt>
            <Translate contentKey="gofindApp.carpool.passengers">Passengers</Translate>
          </dt>
          <dd>
            {carpoolEntity.passengers
              ? carpoolEntity.passengers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {carpoolEntity.passengers && i === carpoolEntity.passengers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="gofindApp.carpool.driver">Driver</Translate>
          </dt>
          <dd>{carpoolEntity.driver ? carpoolEntity.driver.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/carpool" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carpool/${carpoolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CarpoolDetail;
