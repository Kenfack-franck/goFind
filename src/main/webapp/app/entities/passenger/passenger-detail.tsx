import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './passenger.reducer';

export const PassengerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const passengerEntity = useAppSelector(state => state.passenger.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="passengerDetailsHeading">
          <Translate contentKey="gofindApp.passenger.detail.title">Passenger</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.id}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="gofindApp.passenger.status">Status</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.status}</dd>
          <dt>
            <span id="joinDate">
              <Translate contentKey="gofindApp.passenger.joinDate">Join Date</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.joinDate ? <TextFormat value={passengerEntity.joinDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="gofindApp.passenger.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{passengerEntity.utilisateur ? passengerEntity.utilisateur.id : ''}</dd>
          <dt>
            <Translate contentKey="gofindApp.passenger.carpools">Carpools</Translate>
          </dt>
          <dd>
            {passengerEntity.carpools
              ? passengerEntity.carpools.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {passengerEntity.carpools && i === passengerEntity.carpools.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/passenger" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/passenger/${passengerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PassengerDetail;
