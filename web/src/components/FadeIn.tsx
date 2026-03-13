import React, { PropsWithChildren } from 'react';
import './FadeIn.scss';

type FadeInProps = PropsWithChildren<{ isLoaded: boolean }>;

const FadeIn = ({ isLoaded, children }: FadeInProps) => {
  const child = React.Children.only(children) as React.ReactElement<{
    className?: string;
  }>;

  const className = [
    child.props.className,
    'fade-in',
    isLoaded ? 'fade-in-visible' : 'fade-in-hidden',
  ]
    .filter(Boolean)
    .join(' ');

  return React.cloneElement(child, { className });
};

export default FadeIn;
