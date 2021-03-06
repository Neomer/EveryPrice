﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class User : BaseEntity, IUser
    {
        public virtual string Username { get; set; }

        public virtual Guid? Token { get; set; }

        public virtual DateTime? TokenExpirationDate { get; set; }

		public virtual IUserSecurityProfile SecurityProfile { get; set; }

		public virtual IUserProfile Profile { get; set; }
	}
}
