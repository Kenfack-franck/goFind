import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './alert.reducer';

export const AlertDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alertEntity = useAppSelector(state => state.alert.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alertDetailsHeading">
          <Translate contentKey="gofindApp.alert.detail.title">Alert</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{alertEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="gofindApp.alert.message">Message</Translate>
            </span>
          </dt>
          <dd>{alertEntity.message}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="gofindApp.alert.date">Date</Translate>
            </span>
          </dt>
          <dd>{alertEntity.date ? <TextFormat value={alertEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="read">
              <Translate contentKey="gofindApp.alert.read">Read</Translate>
            </span>
          </dt>
          <dd>{alertEntity.read ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="gofindApp.alert.item">Item</Translate>
          </dt>
          <dd>{alertEntity.item ? alertEntity.item.id : ''}</dd>
          <dt>
            <Translate contentKey="gofindApp.alert.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{alertEntity.utilisateur ? alertEntity.utilisateur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/alert" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alert/${alertEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlertDetail;
